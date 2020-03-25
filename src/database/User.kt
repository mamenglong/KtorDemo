package database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object User : LongIdTable() {
  val account: Column<String> = varchar("account",50).uniqueIndex()
  val password: Column<String> = varchar("password", 50)
  val nickName: Column<String> = varchar("nickName", 50)
  val email: Column<String> = varchar("email", 50).uniqueIndex()
}

/**

-- auto-generated definition
create table user
(
account  varchar(50) not null,
password varchar(50) not null,
nickName varchar(50) null,
email    varchar(50) null,
id       int auto_increment,
constraint account
unique (account),
constraint email
unique (email),
constraint id
unique (id)
);

alter table user
add primary key (id);




 */
