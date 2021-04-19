select * from oauth_client_details;

insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, access_token_validity)
values ('orderApp', 'order-server', '$2a$10$eslICbASIub830wCtM1w8ObKP6poIUEKFUOweWXXt2KX6RzLkDh9u', 'read,write', 'password', 3600),
       ('orderService', 'order-server', '$2a$10$qa2VJA.3A8EnmAtRYkxoker7jOcqRB5XYq0SkWmI/KxQb/rDDb8dC', 'read', 'password', 3600);