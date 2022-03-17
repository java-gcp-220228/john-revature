import psycopg
import os
from faker import Faker

fake = Faker()

pg_user = os.getenv('POSTGRES_USER')
pg_pass = os.getenv('POSTGRES_PASSWORD')

# Connect to an existing database
with psycopg.connect(
    host="localhost",
    port=5433,
    user=pg_user,
    password=pg_pass,
    ) as conn:

    # Open a cursor to perform database operations
    with conn.cursor() as cur:
        cur.execute("""
                    drop type if exists account_type cascade;

                    create type account_type as enum('CHEQUING',
                    'SAVINGS');

                    drop table if exists clients cascade;

                    create table clients (
                        id SERIAL primary key,
                        first_name VARCHAR(200) not null,
                        last_name VARCHAR(200) not null,
                        age INTEGER not null 
                    );

                    drop table if exists accounts cascade;

                    create table accounts (
                        id SERIAL primary key,
                        typeOfAccount account_type not null,
                        balance double precision not null,
                        client_id integer not null,
                        
                        constraint fk_client foreign key(client_id) references clients(id)
                    );
                    """)
        for i in range(250):
            fname = fake.first_name()
            lname = fake.last_name()
            age = fake.random_int(min=18, max=100)
            cur.execute("""
                        INSERT INTO clients (first_name,last_name,age)
                        VALUES (%s,%s,%s)
                        """,
                        (fname,lname,age))            
        # # Execute a command: this creates a new table
        # cur.execute("""
        #     CREATE TABLE test (
        #         id serial PRIMARY KEY,
        #         num integer,
        #         data text)
        #     """)

        # # Pass data to fill a query placeholders and let Psycopg perform
        # # the correct conversion (no SQL injections!)
        # cur.execute(
        #     "INSERT INTO test (num, data) VALUES (%s, %s)",
        #     (100, "abc'def"))

        # # Query the database and obtain data as Python objects.
        # cur.execute("SELECT * FROM test")
        # cur.fetchone()
        # # will return (1, 100, "abc'def")

        # # You can use `cur.fetchmany()`, `cur.fetchall()` to return a list
        # # of several records, or even iterate on the cursor
        # for record in cur:
        #     print(record)

        # # Make the changes to the database persistent
        # conn.commit()