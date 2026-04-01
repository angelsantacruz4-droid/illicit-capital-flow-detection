# Installation and Configuration Instructions for Illicit Capital Flow Detection

## Installation

1. **Clone the repository**:
   
   ```bash
   git clone https://github.com/{owner}/illicit-capital-flow-detection.git
   ```

2. **Navigate to the project directory**:
   
   ```bash
   cd illicit-capital-flow-detection
   ```

3. **Install dependencies**:
   
   Make sure you have Node.js installed. Then run:
   
   ```bash
   npm install
   ```

## Configuration

1. **Environment Variables:**
   
   Create a `.env` file in the root directory and add the following mandatory variables:
   
   - `DB_HOST`: The host of your database.
   - `DB_USER`: Your database username.
   - `DB_PASS`: Your database password.
   - `API_KEY`: Your application API key.

   Example:
   
   ```bash
   DB_HOST=localhost
   DB_USER=myuser
   DB_PASS=mypassword
   API_KEY=myapikey
   ```

2. **Run the application:**
   
   After configuring the environment variables, you can start the application by running:
   
   ```bash
   npm start
   ```

3. **Access the application:**
   
   Visit `http://localhost:3000` in your browser.