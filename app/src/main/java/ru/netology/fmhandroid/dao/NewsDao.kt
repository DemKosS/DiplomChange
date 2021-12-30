package ru.netology.fmhandroid.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.netology.fmhandroid.dto.News
import ru.netology.fmhandroid.dto.NewsWithCategory
import ru.netology.fmhandroid.entity.NewsCategoryEntity
import ru.netology.fmhandroid.entity.NewsEntity

@Dao
interface NewsDao {
    @Transaction
    @Query(
        """SELECT * FROM NewsEntity
            WHERE (:publishEnabled IS NULL OR :publishEnabled = publishEnabled)
            AND (:publishDateBefore IS NULL OR publishDate <= :publishDateBefore)
            AND (:newsCategoryId IS NULL OR :newsCategoryId = newsCategoryId)
            AND (:dateStart IS NULL OR publishDate >= :dateStart)
            AND (:dateEnd IS NULL OR publishDate <= :dateEnd)
            ORDER BY publishDate DESC
        """
    )
    fun getAllNews(
        publishEnabled: Boolean? = null,
        publishDateBefore: Long? = null,
        newsCategoryId: Int? = null,
        dateStart: Long? = null,
        dateEnd: Long? = null
    ): Flow<List<NewsWithCategory>>

    @Query("SELECT * FROM NewsEntity")
    fun getAllNewsList(): List<NewsWithCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsItem: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<NewsEntity>)

    @Query("DELETE FROM NewsEntity WHERE id = :id")
    suspend fun removeNewsItemById(id: Int)

    @Query("DELETE FROM NewsEntity WHERE id IN (:idList)")
    suspend fun removeNewsItemsByIdList(idList: List<Int?>)

//    @Query("DELETE FROM NewsEntity")
//    suspend fun removeNewsItemById()
}

@Dao
interface NewsCategoryDao {
    @Query("SELECT * FROM NewsCategoryEntity ORDER BY id")
    fun getAllNewsCategories(): Flow<List<NewsCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<NewsCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: NewsCategoryEntity)

    @Query("SELECT * FROM NewsCategoryEntity")
    fun getNewsCategoryList(): List<NewsCategoryEntity>
}
