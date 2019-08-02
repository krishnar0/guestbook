insert into Guest_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED)
values (1, 'admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into Guest_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED)
values (2, 'user1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
---
 
insert into Guest_role (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');
 
insert into Guest_role (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_USER');

---

insert into user_role (USER_ID, ROLE_ID)
values (1, 1);
 
insert into user_role (USER_ID, ROLE_ID)
values (2, 2);

