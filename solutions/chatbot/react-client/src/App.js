import React, { useState, useEffect } from 'react';
import BeatLoader from "react-spinners/BeatLoader";

function ChatInterface() {
  const [message, setMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [ws, setWs] = useState(null);
  const [loading, setLoading] = useState(false); // Add a loading state variable

  useEffect(() => {
    // Establish a WebSocket connection
    const ws = new WebSocket('ws://localhost:8080/jarvis');
    setWs(ws);

    // Handle incoming messages from the server
    ws.onmessage = (event) => {
      setMessages((prevMessages) => [...prevMessages, event.data]);
      setLoading(false); // Set loading to false when the first response is received
    };

    // Handle errors and disconnections
    ws.onerror = (event) => {
      console.error('Error occurred:', event);
    };
    ws.onclose = () => {
      console.log('Disconnected from the server');
    };

    return () => {
      // Clean up when the component unmounts
      ws.close();
    };
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    if (ws) {
      // Send the message to the server
      ws.send(message);
      setMessages('');
      setLoading(true); // Set loading to true when sending a message

    }
  };

  return (
    <div>
      <h1>🤖</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={message}
          onChange={(event) => setMessage(event.target.value)}
          placeholder="Type a message..."
          size="100"
        />
        <button type="submit">Send</button>
      </form>
      <br/>
      {loading ? (
        <div>
          <BeatLoader />
        </div>
      ) : (
        <span>{messages}</span>
      )}
    </div>
  );
}

export default ChatInterface;
