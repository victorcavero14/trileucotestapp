# Use Node 18 as the base image.
FROM node:18

# Set working directory
WORKDIR /app

# Set environment variable for debug mode
ARG debug
ENV REACT_APP_DEBUG_MODE ${debug}

# Copy `package.json` and `package-lock.json` (if available)
COPY ./package*.json ./

# Install dependencies
RUN npm install

# Copy local files of frontend to the container
COPY ./ .

# Expose port 3000 for create-react-app
EXPOSE 3000

# Start development server
CMD ["npm", "start"]
