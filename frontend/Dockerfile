# Base image
FROM node:14.17-alpine

# Set working directory
WORKDIR /app

# Copy dependencies file
COPY package*.json ./

# Install dependencies
RUN npm install

# Install node-sass package
RUN npm install node-sass

# Copy application files
COPY . .

# Build application
RUN npm run build

# Expose port
EXPOSE 3000

# Start application
CMD ["npm", "start"]
