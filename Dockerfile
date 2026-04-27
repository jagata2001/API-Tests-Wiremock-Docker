FROM maven:3.8.5-openjdk-18

# 2. განსაზღვრეთ სამუშაო დირექტორია
WORKDIR /app
COPY . .

RUN mvn clean package