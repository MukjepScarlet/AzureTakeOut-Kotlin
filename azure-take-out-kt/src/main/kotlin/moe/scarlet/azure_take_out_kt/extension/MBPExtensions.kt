package moe.scarlet.azure_take_out_kt.extension

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page

inline fun <reified T : Any> BaseMapper<T>.delete(
    wrapper: KtQueryWrapper<T> = KtQueryWrapper(T::class.java),
    queryBuilder: QueryWrapperContext<T>.() -> Unit
): Int = this.delete(QueryWrapperContext(wrapper).apply(queryBuilder).wrapper)

inline fun <reified T : Any> BaseMapper<T>.selectOne(
    wrapper: KtQueryWrapper<T> = KtQueryWrapper(T::class.java),
    queryBuilder: QueryWrapperContext<T>.() -> Unit
): T? = this.selectOne(QueryWrapperContext(wrapper).apply(queryBuilder).wrapper)

inline fun <reified T : Any> BaseMapper<T>.selectList(
    wrapper: KtQueryWrapper<T> = KtQueryWrapper(T::class.java),
    queryBuilder: QueryWrapperContext<T>.() -> Unit
): List<T> = this.selectList(QueryWrapperContext(wrapper).apply(queryBuilder).wrapper)

inline fun <reified T : Any> BaseMapper<T>.selectPage(
    page: Long,
    pageSize: Long,
    wrapper: KtQueryWrapper<T> = KtQueryWrapper(T::class.java),
    queryBuilder: QueryWrapperContext<T>.() -> Unit
): Page<T> = this.selectPage(Page(page, pageSize), QueryWrapperContext(wrapper).apply(queryBuilder).wrapper)

inline fun <reified T : Any> BaseMapper<T>.update(
    wrapper: KtUpdateWrapper<T> = KtUpdateWrapper(T::class.java),
    queryBuilder: UpdateWrapperContext<T>.() -> Unit
): Int = this.update(UpdateWrapperContext(wrapper).apply(queryBuilder).wrapper)
