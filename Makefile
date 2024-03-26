# -d: 백그라운드 실행, --force-recreate: 강제 재생성
db-up:
	docker-compose up -d --force-recreate

# -v: volume 삭제
db-down:
	docker-compose down -v

# Makefile을 실행하려면 파일이 위치한 디렉토리에서
# make db-up 명령을 통행 docker-compose를 실행하여 컨테이너를 생성하고
# make db-down 명령을 통해 docker-compose를 통해 생성된 컨테이너를 내릴 수 있다.
