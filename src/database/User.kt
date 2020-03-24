package database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object User : LongIdTable() {
  val account: Column<String> = varchar("account",50).uniqueIndex()
  val password: Column<String> = varchar("nickName", 50)
  val nickName: Column<String> = varchar("nickName", 50)
  val email: Column<String> = varchar("email", 50).uniqueIndex()
}