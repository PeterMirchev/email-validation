import logo from './logo.svg';
import './App.css';
import React, { useState, useEffect } from 'react';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

function App() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [uploadProgress, setUploadProgress] = useState(0);

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/progress', (message) => {
        const progress = parseFloat(message.body);
        setUploadProgress(progress);
      });
    });

    return () => {
      if (stompClient && stompClient.connected) {
        stompClient.disconnect();
      }
    };
  }, []);

  const handleFileSelect = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const saveResponseToFile = (response) => {
    const blob = new Blob([JSON.stringify(response, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'response.json'; // Default file name for the download
    a.click();
    URL.revokeObjectURL(url);
  };

  const uploadFile = () => {
    if (!selectedFile) {
      console.error('No file selected');
      return;
    }

    const reader = new FileReader();
    reader.onload = (event) => {
      const fileContent = event.target.result;
      try {
        const emails = JSON.parse(fileContent);
        console.log('Parsed Emails:', emails);

        console.log('Sending request to server...');
        fetch('http://localhost:8080/mail-bulk-validations/emails', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(emails),
        })
          .then(response => {
            console.log('Response received from server');
            console.log('Response Status:', response.status);
            return response.json().then(data => ({ status: response.status, body: data }));
          })
          .then(({ status, body }) => {
            console.log('Server Response Data:', body);
            if (status === 200) {
              // File uploaded successfully, save the response to a file
              console.log('File uploaded successfully');
              saveResponseToFile(body);
            } else {
              // Error uploading file
              console.error('Error uploading file:', body);
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });
      } catch (error) {
        console.error('Error parsing file content:', error);
      }
    };

    reader.readAsText(selectedFile);
  };

  return (
    <div className="App">
      <header className="App-header">
        <p>
          <code>Welcome to email bulk validation.</code>
        </p>
        <img src={logo} className="App-logo" alt="logo" />
        <div>
          <h2>Select a File:</h2>
          <input type="file" id="fileInput" style={{ display: 'none' }} onChange={handleFileSelect} />
          <button onClick={() => document.getElementById('fileInput').click()}>Select File</button>
          <button onClick={uploadFile}>Upload File</button>
          <p>File Progress: {uploadProgress.toFixed(2)}%</p>
        </div>
      </header>
    </div>
  );
}

export default App;
