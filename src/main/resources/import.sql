ALTER TABLE t_enroll ADD FOREIGN KEY (code) REFERENCES t_course(code);
ALTER TABLE t_enroll ADD FOREIGN KEY (username) REFERENCES t_user(username);

insert into t_user (username, email) values ('alex', 'alex@email.com');
insert into t_user (username, email) values ('ana', 'ana@email.com');
insert into t_user (username, email) values ('gustavo', 'gustavo@email.com');
insert into t_user (username, email) values ('alberto', 'alberto@email.com');
insert into t_user (username, email) values ('maria', 'maria@email.com');
insert into t_user (username, email) values ('erica', 'erica@email.com');
insert into t_user (username, email) values ('camila', 'camila@email.com');
insert into t_user (username, email) values ('marisa', 'marisa@email.com');
insert into t_user (username, email) values ('marcos', 'marcos@email.com');

insert into t_course (code, name, description) values ('java-1', 'Java OO', 'Java and Object Orientation: Encapsulation, Inheritance and Polymorphism.');
insert into t_course (code, name, description) values ('java-2', 'Java Collections', 'Java Collections: Lists, Sets, Maps and more.');
insert into t_course (code, name, description) values ('java-3', 'Java 8', 'Java 8');

insert into t_enroll (code, username) values ('java-1', 'gustavo');
insert into t_enroll (code, username) values ('java-2', 'gustavo');
insert into t_enroll (code, username) values ('java-3', 'gustavo');
insert into t_enroll (code, username) values ('java-2', 'alberto');
insert into t_enroll (code, username) values ('java-3', 'alberto');
insert into t_enroll (code, username) values ('java-3', 'marcos');