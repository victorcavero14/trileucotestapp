# Use Node 18 as the base image.
FROM node:18

# Set working directory
WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy local files of frontend to the container
COPY . .

# Expose port 3000 for create-react-app
EXPOSE 3000

# Start development server
CMD ["npm", "start"]
