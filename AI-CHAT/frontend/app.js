const API_URL = 'http://localhost:8080/api/ask';

const chatBox = document.getElementById('chatBox');
const sendBtn = document.getElementById('sendBtn');
const promptInput = document.getElementById('promptInput');

function appendMessage(text, sender) {
  const container = document.createElement('div');
  container.classList.add('message');
  container.classList.add(sender);

  const bubble = document.createElement('div');
  bubble.classList.add('bubble');
  bubble.textContent = text;

  container.appendChild(bubble);
  chatBox.appendChild(container);
  chatBox.scrollTop = chatBox.scrollHeight;
}

async function sendMessage() {
  const message = promptInput.value.trim();
  if (!message) return;

  appendMessage(message, 'user');
  promptInput.value = '';
  sendBtn.disabled = true;

  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ message })
    });

    const data = await response.json();
    appendMessage(data.reply, 'ai');
  } catch (err) {
    console.error(err);
    appendMessage('An error occurred while contacting the server.', 'ai');
  }

  sendBtn.disabled = false;
}

sendBtn.addEventListener('click', sendMessage);
promptInput.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') {
    sendMessage();
  }
});
