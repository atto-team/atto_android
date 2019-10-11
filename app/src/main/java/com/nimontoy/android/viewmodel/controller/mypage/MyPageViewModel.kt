package com.nimontoy.android.viewmodel.controller.mypage

import com.nimontoy.android.basic.Type
import com.nimontoy.android.helper.base.MinorHelper
import com.nimontoy.android.model.Data
import com.nimontoy.android.model.ResponseData
import com.nimontoy.android.model.mypage.EventCard
import com.nimontoy.android.model.mypage.Profile
import com.nimontoy.android.model.mypage.Toy
import com.nimontoy.android.viewmodel.controller.DataListViewModel
import io.reactivex.Flowable

class MyPageViewModel : DataListViewModel() {

    override fun getDataList(
        startHandler: (() -> Unit)?,
        successHandler: (() -> Unit)?,
        failureHandler: (() -> Unit)?,
        endHandler: (() -> Unit)?
    ) {

        getTestProfileList().subscribe({
            onResponseWith(it.dataList)
        }, {
            it.message?.let { it1 -> MinorHelper.toast(it1) }
        })
    }

    // TODO 테스트 코드이므로 반드시 지울 것..
    private fun getTestProfileList(): Flowable<ResponseData> {
        val responseData = ResponseData(dataList = mutableListOf<Data>()
            .apply {
                add(
                    Profile(
                        type = Type.HEADER_CELL,
                        userName = "TEST USER"
                    )
                )
                add(
                    Profile(
                        image = "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E",
                        type = Type.PROFILE_CELL,
                        follow = "123456",
                        follower = "123456"
                    )
                )
                add(Profile(type = Type.SPACE_CELL))

                add(
                    Profile(
                        type = Type.INTRO_CELL,
                        activity = 1111,
                        like = 2222,
                        point = 33333
                    )
                )
                add(Profile(type = Type.SPACE_CELL))

                add(
                    EventCard(
                        type = Type.EVENT_CARD_CELL,
                        title = "My Toys"
                    )
                )


                //toy
                add(
                    Profile(
                        type = Type.MY_TOYS_CELL
                    )
                )
                add(Profile(type = Type.SPACE_CELL))

                add(Profile(
                    type = Type.MY_TOYS_CELL,
                    toys = mutableListOf<Toy?>().apply {
                        add(Toy(name = "토이1"))
                    }
                ))
                add(Profile(type = Type.SPACE_CELL))

                add(Profile(
                    type = Type.MY_TOYS_CELL,
                    toys = mutableListOf<Toy?>().apply {
                        add(Toy(name = "토이1"))
                        add(
                            Toy(
                                name = "토이2",
                                image = "https://t1.daumcdn.net/cfile/tistory/24283C3858F778CA2E"
                            )
                        )
                        add(Toy(name = "토이3"))
                    }
                ))
                add(Profile(type = Type.SPACE_CELL))
                //toy


                add(
                    EventCard(
                        type = Type.EVENT_CARD_CELL,
                        title = "Event"
                    )
                )
                add(
                    EventCard(
                        type = Type.EVENT_CARD_CELL,
                        title = "Setting"
                    )
                )
                add(Profile(type = Type.SPACE_CELL))
            })

        return Flowable.just(responseData)
    }
}