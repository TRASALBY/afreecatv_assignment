# [아프리카TV] 2023 신입/경력 공개채용 Android 사전 과제

## 개발 환경
- Language : Kotlin
- minSdk : 23
- targetSdk : 31

## 사용 기술
- `MVVM` `databinding` `Single Activity`
- `Kotlin Coroutine` `Flow`
- `Retrofit2` `OkHttp3` `Gson`
- `Hilt`
- `Paging3`
- `TabLayout` `ViewPager2`'
- `Navigtaion`
- `Glide`

## 요구사항
- 카테고리 API를 이용해 카테고리를 가져오고 3개이상의 카테고리를 선전하여 탭 구성
- 각 탭 클릭시 해당 카테고리의 방송리스트 출력 (방송제목과 썸네일 필수)
- 페이징 기능으로 다음 페이지를 하단에 추가
- 방송 클릭시 상세페이지로 이동


## 실행 화면
<details>
<summary>카테고리 선택</summary>
<div>

https://user-images.githubusercontent.com/54586491/211194871-5e52b3b1-ec48-4e97-9ac9-6984e402dc9e.mp4

</div>
</details>

<details>
<summary>방송 불러오기</summary>
<div>

https://user-images.githubusercontent.com/54586491/211194707-e2f4a1d7-e296-473a-9380-d1fda1fa482e.mp4

</div>
</details>

<details>
<summary>방송 상세 페이지</summary>
<div>

https://user-images.githubusercontent.com/54586491/211194710-16d9f63f-e9be-4473-87eb-bf893c0a637a.mp4

</div>
</details>

<details>
<summary>새로고침</summary>
<div>

https://user-images.githubusercontent.com/54586491/211194832-76cab077-bb42-425c-9442-ce1fc16f2b5d.mp4

</div>
</details>

<details>
<summary>네트워크 처리</summary>
<div>

https://user-images.githubusercontent.com/54586491/211194847-b3442f3b-9962-4092-b769-4473f62cf840.mp4

</div>
</details>



## 기술적 고민
### 카테고리를 어떻게 선택 할 것인가.
- 요구사항 `카테고리 API를 사용하여 가져온 카테고리 중 3개 이상의 카테고리를 선정하여 탭을 구성합니다.`
- 어플을 실행하면 다이얼로그를 통해 사용자가 카테고리를 선택하도록 유도
- 3개 이상이 선택 되었을 때에만 선택 완료버튼을 활성화
- 선택이 완료되었을 때 선택된 카테고리들의 categryNumber값을 프래그먼트로 전달하여 해당 카테고리들의 방송을 TabLayout으로 나타냄
- 선택이 완료되지 않고 다이얼로그가 내려간다면 카테고리를 선택해 달라는 문구 표시
- FloatingActionButton 을 통해 선택한 다이얼로그를 다시 띄워 선택 카테고리 변경

```kotlin
//CategorySelectDialog
private fun showCategorySelectDialog(selectedCategoryList: List<BroadCategoryUiModel>) {
    categorySelectDialog = CategorySelectDialog(this)
    activity?.supportFragmentManager?.let { fragmentManager ->
        categorySelectDialog?.apply {
            arguments =
                bundleOf(KEY_SELECTED_CATEGORY to selectedCategoryList.map { it.categoryNumber }
                    .toTypedArray())
            show(
                fragmentManager,
                CATEGORY_SELECT_DIALOG
            )
        }
    }
}
```
- 다이얼로그를 생성하는 프래그먼트에서 현재 선택되어있는 category들을 번들로 전달.

```kotlin 
//CategorySelectViewModel.kt
fun setSelectedCategories(selectedCategoryNumbers: Array<String>) {
    viewModelScope.launch {
    ...
    _categories.update {
        UiState.Success(
            categoryList.map {
                if (selectedCategoryNumbers.contains(it.categoryNumber)) {
                    val newBroadCategoryUiModel = it.copy(isSelected = true)
                    _selectedCategories.value += newBroadCategoryUiModel
                    newBroadCategoryUiModel
                } else {
                    it
                }
            }
        )
    }
}
```
- api를 통해 구해진 categoryList에서 프래그먼트로부터 전달받은 이전에 선택된 카테고리 값을 categoryNumber를 통해 구하여 _selectedCategories값을 갱신
```kotlin
//CategorySelectViewModel.kt
fun changeCategorySelected(categoryUiModel: BroadCategoryUiModel, checked: Boolean) {
    val nowCategory =
        _selectedCategories.value.firstOrNull { it.categoryNumber == categoryUiModel.categoryNumber }
    val isCategorySelected = nowCategory != null

    if (checked && isCategorySelected.not()) {
        _selectedCategories.value = _selectedCategories.value + categoryUiModel
    } else if (checked.not() && isCategorySelected) {
        nowCategory ?: return
        _selectedCategories.value = _selectedCategories.value - nowCategory
    }

    if (_categories.value is UiState.Success) {
        _categories.update {
            UiState.Success(
                (_categories.value as UiState.Success<List<BroadCategoryUiModel>>).item.map {
                    if (it.categoryNumber == categoryUiModel.categoryNumber) {
                        it.copy(isSelected = it.isSelected.not())
                    } else {
                        it
                    }
                }
            )
        }
    }
}
```
- 새로운 카테고리가 선택 될 때마다 추가 혹은 삭제를 진행

### TabLayout 커스텀
- 카테고리 개수가 유동적으로 변하게 되었으므로 TabLayout의 탭의 개수도 따라서 변경
- 카테고리의 개수가 많아지게 되면 TabMode를 scrollable로 두어 이동할 수 있도록 할 필요가 발생
- TabMode가 scrollable일 경우 카테고리의 개수가 적을 때에는 공백이 생기는 문제가 발생

```kotlin
class CustomTabLayout : TabLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val tabLayout = getChildAt(0) as ViewGroup
        val childCount = tabLayout.childCount

        if(childCount > 0) {
            val widthPixels = MeasureSpec.getSize(widthMeasureSpec)
            val tabMinWidth = widthPixels / childCount
            var remainderPixels = widthPixels % childCount

            tabLayout.forEach {
                it.minimumWidth = tabMinWidth
                if (remainderPixels > 0) {
                    it.minimumWidth = tabMinWidth + 1
                    remainderPixels--
                } else {
                    it.minimumWidth = tabMinWidth
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
```
- TabLayout을 상속받아 CustomTabLayout 구현
- 탭의 개수를 바탕으로 각 Tab의 minimumWidth를 계산하여 여백없이 Tab이 나타나도록 구현

```kotlin
//BroadListFragment.kt
private fun observeSelectedCategories() {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            broadListViewModel.selectedCategories.collect {
                if (it.isEmpty()) {
                    showCategorySelectDialog(it)
                }
                setViewPager(it)
            }
        }
    }
}

private fun setViewPager(categoryList: List<BroadCategoryUiModel>?) {
    if (categoryList.isNullOrEmpty()) {
        binding.tvPleaseCategorySelect.isVisible = true
    } else {
        binding.tvPleaseCategorySelect.isVisible = false

        val broadCategoryAdapter = BroadCategoryAdapter(this, categoryList)
        binding.viewpagerBroadList.adapter = broadCategoryAdapter

        TabLayoutMediator(
            binding.tabBroadCategory, binding.viewpagerBroadList
        ) { tab, position ->
            tab.text = categoryList[position].categoryName
        }.attach()
    }
}
```
- 다이얼로그를 통해 선택된 카테고리가 변경되었다면 해당 값을 바탕으로 TabLayout의 프래그먼트를 생성
- 다이얼로그를 취소하는 등의 이유로 카테고리가 선택되지 않았다면 카테고리를 선택해 달라는 내용의 TextView를 보여줌