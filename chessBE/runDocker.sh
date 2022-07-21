docker stop onurcan_chess
docker rm onurcan_chess
docker run --name onurcan_chess -d -p 8080:8080 onurcan/chess:1.0