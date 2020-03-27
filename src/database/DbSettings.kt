package database

import org.jetbrains.exposed.sql.Database

object DbSettings {
    /**
     *
     */
   val db by lazy {
       Database.connect("jdbc:mysql://localhost:3306/test", driver = "com.mysql.jdbc.Driver",
           user = "root", password = "root1234" )
           .useNestedTransactions = true//嵌套查询
   }
}