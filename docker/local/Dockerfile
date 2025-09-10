FROM postgres:15

EXPOSE 5432

ENV POSTGRES_PASSWORD=MyP@w0rd
ENV POSTGRES_USER=postgres
ENV POSTGRES_DB=localdb
ENV POSTGRES_HOST_AUTH_METHOD=trust

# Use a path relative to the build context
#COPY ../../database/schema.sql /docker-entrypoint-initdb.d/

# PostgreSQL automatically runs scripts in docker-entrypoint-initdb.d/
# and creates the database specified in POSTGRES_DB on startup

# Default command - starts PostgreSQL
CMD ["postgres"]