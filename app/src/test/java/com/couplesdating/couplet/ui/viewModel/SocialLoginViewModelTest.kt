package com.couplesdating.couplet.ui.viewModel

import com.couplesdating.couplet.CoroutineScopeExtension
import com.couplesdating.couplet.InstantExecutorExtension
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.useCase.GetCurrentUserUseCase
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class, CoroutineScopeExtension::class)
internal class SocialLoginViewModelTest {
    private val getCurrentUserUseCase = mock<GetCurrentUserUseCase>()
    private lateinit var viewModel: SocialLoginViewModel

    @Nested
    @DisplayName("Given useCase returns")
    inner class EmitUser {

        @Test
        fun `then liveData emits value`() {
            val user = User(
                name = "",
                email = ""
            )

            whenever(getCurrentUserUseCase.getCurrentUser()).thenReturn(user)
            viewModel = SocialLoginViewModel(getCurrentUserUseCase)

            verify(getCurrentUserUseCase).getCurrentUser()
            assertThat(viewModel.userLiveData.value).isEqualTo(user)
        }
    }
}