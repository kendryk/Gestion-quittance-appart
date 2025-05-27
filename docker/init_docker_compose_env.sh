#!/bin/sh

set -e  # Arrêter le script en cas d'erreur

REPERTOIRE=${1:-.}

cd $REPERTOIRE
chmod 0444 maria_custom_conf/my.cnf

docker-compose down -v
docker-compose up -d




