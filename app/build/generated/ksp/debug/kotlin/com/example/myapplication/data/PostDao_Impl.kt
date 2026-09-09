package com.example.myapplication.`data`

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class PostDao_Impl(
  __db: RoomDatabase,
) : PostDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfPost: EntityInsertAdapter<Post>

  private val __deleteAdapterOfPost: EntityDeleteOrUpdateAdapter<Post>

  private val __updateAdapterOfPost: EntityDeleteOrUpdateAdapter<Post>
  init {
    this.__db = __db
    this.__insertAdapterOfPost = object : EntityInsertAdapter<Post>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `posts` (`id`,`content`,`created_at`) VALUES (nullif(?, 0),?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Post) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.content)
        statement.bindLong(3, entity.createdAt)
      }
    }
    this.__deleteAdapterOfPost = object : EntityDeleteOrUpdateAdapter<Post>() {
      protected override fun createQuery(): String = "DELETE FROM `posts` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Post) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfPost = object : EntityDeleteOrUpdateAdapter<Post>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `posts` SET `id` = ?,`content` = ?,`created_at` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Post) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.content)
        statement.bindLong(3, entity.createdAt)
        statement.bindLong(4, entity.id)
      }
    }
  }

  public override suspend fun insert(post: Post): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfPost.insertAndReturnId(_connection, post)
    _result
  }

  public override suspend fun delete(post: Post): Unit = performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfPost.handle(_connection, post)
  }

  public override suspend fun update(post: Post): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfPost.handle(_connection, post)
  }

  public override fun observeAll(): Flow<List<Post>> {
    val _sql: String = "SELECT * FROM posts ORDER BY created_at DESC"
    return createFlow(__db, false, arrayOf("posts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _result: MutableList<Post> = mutableListOf()
        while (_stmt.step()) {
          val _item: Post
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = Post(_tmpId,_tmpContent,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
