import React, { useState, useEffect } from 'react';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import staticImage from './staticImage.png'; // Import the new static image
import './App.css';

function App() {
  const [selectedFiles, setSelectedFiles] = useState([]);
  const [uploadProgress, setUploadProgress] = useState(0);
  const [fileNames, setFileNames] = useState([]);

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
    const files = Array.from(event.target.files);
    setSelectedFiles(files);
    setFileNames(files.map(file => file.name));
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
    if (selectedFiles.length === 0) {
      console.error('No files selected');
      return;
    }

    const formData = new FormData();
    selectedFiles.forEach(file => formData.append('file', file));

    fetch('http://localhost:8080/mail-bulk-validations/emails', {
      method: 'POST',
      body: formData,
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
          // Clear selected files
          setSelectedFiles([]);
          setFileNames([]);
          // Refresh the page
          window.location.reload();
        } else {
          // Error uploading file
          console.error('Error uploading file:', body);
        }
      })
      .catch(error => {
        console.error('Error:', error);
      });
  };

  return (
    <div className="App">
      <header className="App-header">
        <div className="App-top">
          <p className="App-welcome">
            <code>Welcome to bulk email validation.</code>
          </p>
        </div>
        <div className="App-middle">
          <img src={staticImage} className="App-static-image" alt="static" />
        </div>
        <div className="App-info">
          <p>Valid file formats: txt, csv, file</p>
        </div>
        <div className="App-bottom">
          <h2>Select Files:</h2>
          <input type="file" id="fileInput" style={{ display: 'none' }} onChange={handleFileSelect} multiple />
          <button onClick={() => document.getElementById('fileInput').click()}>Select Files</button>
          {fileNames.length > 0 && (
            <p>Selected Files: {fileNames.join(', ')}</p>
          )}
          <button onClick={uploadFile}>Upload Files</button>
          <p>File Progress: {uploadProgress.toFixed(2)}%</p>
        </div>
      </header>
    </div>
  );
}

export default App;
