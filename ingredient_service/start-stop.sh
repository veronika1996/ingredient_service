#!/bin/bash

SERVICES=("user_service" "ingredient_service" "recipes_service" "plans_service")
PORTS=(8082 8083 8084 8085)

FRONTEND_DIR="mealplan-app-front"
FRONTEND_PORT=5173

start_services() {
  for i in "${!SERVICES[@]}"
  do
    service=${SERVICES[$i]}
    port=${PORTS[$i]}
    echo "Startujem servis $service na portu $port"
    (
      cd "$service"
      mvn spring-boot:run &
    )
    sleep 2
  done
}

start_frontend() {
  echo "Startujem frontend $FRONTEND_DIR na portu $FRONTEND_PORT"
  (
    cd "$FRONTEND_DIR"
    npm run dev &
  )
}

stop_services() {
  for port in "${PORTS[@]}"
  do
    PID=$(lsof -t -i:$port)
    if [ -n "$PID" ]; then
      echo "Gasenje servisa na portu $port (PID: $PID)"
      kill -9 $PID
    else
      echo "Nema procesa na portu $port"
    fi
  done
}

stop_frontend() {
  PID=$(lsof -t -i:$FRONTEND_PORT)
  if [ -n "$PID" ]; then
    echo "Gasenje frontenda na portu $FRONTEND_PORT (PID: $PID)"
    kill -9 $PID
  else
    echo "Nema frontenda na portu $FRONTEND_PORT"
  fi
}

trap 'echo -e "\nZaustavljam servise i frontend..."; stop_services; stop_frontend; exit 0' SIGINT

start_services
start_frontend

echo "Svi servisi i frontend su startovani. Pritisni Ctrl+C za zaustavljanje."

while true; do sleep 1; done
