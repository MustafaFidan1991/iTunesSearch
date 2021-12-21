package com.mustafafidan.itunessearch.feature_search.domain.use_case

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.google.common.truth.Truth.assertThat
import com.mustafafidan.itunessearch.errorhandling.Success
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel
import com.mustafafidan.itunessearch.feature_search.domain.model.remote.ResultEntity
import com.mustafafidan.itunessearch.feature_search.domain.model.remote.ResultResponseEntity
import com.mustafafidan.itunessearch.feature_search.domain.repository.SearchRepository
import com.mustafafidan.itunessearch.feature_search.presentation.search.FilterMediaType
import com.mustafafidan.itunessearch.feature_search.presentation.search.SearchState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchResultsUseCaseTest{

    @MockK
    private lateinit var repository: SearchRepository

    private lateinit var useCase: FetchResultsUseCase

    private val mainThreadSurrogate = newSingleThreadContext("thread")

    private lateinit var mapper : FetchResultsMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(mainThreadSurrogate)
        mapper = FetchResultsMapper(DateFormatter(),CurrencyConverter())
        useCase = FetchResultsUseCase(repository, mapper, SearchState().apply {  searchTerm.value = "test"; filterMediaType.value = FilterMediaType.NONE})
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchResultUseCaseShouldMapRemoteData() =
        runTest {
            val list = (1..30).map {
                ResultEntity(it,1,"",0.0,"","","","","",false,"","")
            }

            val successResult = Success(ResultResponseEntity(30, list))
            coEvery { repository.getResults("test","all",0) } returns successResult

            val differ = AsyncPagingDataDiffer(
                diffCallback = ResultUiModelDiffCallback(),
                updateCallback = ListCallback(),
                workerDispatcher = Dispatchers.Main
            )
            val job = this.launch{
                useCase().collectLatest {
                    differ.submitData(it)
                }
            }

            advanceUntilIdle()

            assertThat(list.map { it.trackId }).isEqualTo(differ.snapshot().items.map { it.id })
            job.cancel()
        }

}

class ListCallback : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
}

class ResultUiModelDiffCallback : DiffUtil.ItemCallback<ResultUiModel>() {
    override fun areItemsTheSame(oldItem: ResultUiModel, newItem: ResultUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultUiModel, newItem: ResultUiModel): Boolean {
        return oldItem.id == newItem.id
    }
}