tests:
	./gradlew test

build:
	./gradlew build

start-event-platform:
	docker network create game || true
	docker-compose up -d event_platform

stop-event-platform:
	docker-compose stop event_platform
	docker rm event_platform

start-manual-game-environment:
	docker-compose up -d player_one_manual player_two_manual

stop-manual-game-environment:
	docker-compose stop player_one_manual
	docker-compose stop player_two_manual
	docker rm game_find_three_player_one

start-auto-game-environment:
	docker-compose up -d player_one_auto player_two_manual

stop-auto-game-environment:
	docker-compose stop player_one_auto
	docker-compose stop player_two_manual
	docker rm game_find_three_player_one

start-manually-by-player1:
	curl -X POST http://localhost:8089/v1/start -v

start-manually-by-player2:
	curl -X POST http://localhost:8085/v1/start -v