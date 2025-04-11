import psycopg2
from psycopg2 import OperationalError

def test_postgres_connection(username, password, host, database, port=5432):
    try:
        # Attempt to connect to the PostgreSQL database
        connection = psycopg2.connect(
            dbname=database,
            user=username,
            password=password,
            host=host,
            port=port
        )
        print("Connection successful!")
        connection.close()  # Close the connection after successful test
    except OperationalError as e:
        print("Connection failed!")
        print(f"Error: {e}")

# Replace with your actual connection details
test_postgres_connection(
    username="postgres",  # Replace with your username
    password="Czx32mw9",  # Replace with your password
    host="localhost",  # Replace with your host (e.g., localhost or an IP address)
    database="gymdb"  # Replace with your database name
)
