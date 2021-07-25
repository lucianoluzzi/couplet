package com.couplesdating.couplet.domain

import com.couplesdating.couplet.domain.useCase.auth.*
import com.couplesdating.couplet.domain.useCase.category.GetCategoriesUseCase
import com.couplesdating.couplet.domain.useCase.category.GetCategoriesUseCaseImpl
import com.couplesdating.couplet.domain.useCase.category.RefreshCategoriesUseCase
import com.couplesdating.couplet.domain.useCase.category.RefreshCategoriesUseCaseImpl
import com.couplesdating.couplet.domain.useCase.idea.*
import com.couplesdating.couplet.domain.useCase.invite.*
import com.couplesdating.couplet.domain.useCase.match.GetNewMatchesUseCase
import com.couplesdating.couplet.domain.useCase.match.GetNewMatchesUseCaseImpl
import com.couplesdating.couplet.domain.useCase.notifications.GetCloudMessagingTokenUseCase
import com.couplesdating.couplet.domain.useCase.notifications.GetCloudMessagingTokenUseCaseImpl
import com.couplesdating.couplet.domain.useCase.pair.*
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCase
import com.couplesdating.couplet.domain.useCase.user.GetCurrentUserUseCaseImpl
import com.couplesdating.couplet.domain.useCase.user.UpdateUserUseCase
import com.couplesdating.couplet.domain.useCase.user.UpdateUserUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<GetCurrentUserUseCase> {
        GetCurrentUserUseCaseImpl(
            getPartnerUseCase = get(),
            userRepository = get()
        )
    }

    single<SignInUseCase> { SignInUseCaseImpl(get()) }

    single<RegisterUseCase> { RegisterUseCaseImpl(get()) }

    single<UpdateUserUseCase> { UpdateUserUseCaseImpl(get()) }

    single<ResetPasswordUseCase> { ResetPasswordUseCaseImpl(get()) }

    single<GoogleSignInUseCase> { GoogleSignInUseCaseImpl(get()) }

    single<FacebookSignInUseCase> { FacebookSignInUseCaseImpl(get()) }

    single<GenerateInviteLinkUseCase> { GenerateInviteLinkUseCaseImpl(get()) }

    single<SavePairInviteUseCase> { SavePairInviteUseCaseImpl(get()) }

    single<GetReceivedInviteUseCase> { GetReceivedInviteUseCaseImpl(get()) }

    single<DeleteInviteUseCase> { DeleteInviteUseCaseImpl(get()) }

    single<FormPairUseCase> { FormPairUseCaseImpl(get(), get()) }

    single<CreateInviteUseCase> { CreateInviteUseCaseImpl(get()) }

    single<ShouldShowSyncUseCase> {
        ShouldShowSyncUseCaseImpl(
            pairRepository = get(),
            inviteRepository = get()
        )
    }

    single<SetSyncShownUseCase> { SetSyncShownUseCaseImpl(get()) }

    single<GetPartnerUseCase> { GetPartnerUseCaseImpl(get()) }

    single<InviteExistsUseCase> { InviteExistsUseCaseImpl(get()) }

    single<GetCategoriesUseCase> { GetCategoriesUseCaseImpl(get()) }

    single<RefreshCategoriesUseCase> { RefreshCategoriesUseCaseImpl(get()) }

    single<AddInviteeIdUseCase> { AddInviteeIdUseCaseImpl(get()) }

    single<GetSentPairInviteUseCase> { GetSentPairInviteUseCaseImpl(get()) }

    single<GetNewMatchesUseCase> { GetNewMatchesUseCaseImpl(get()) }

    single<DecorateIdeaUseCase> { DecorateIdeaUseCaseImpl() }

    single<SendIdeaResponseUseCase> {
        SendIdeaResponseUseCaseImpl(
            getCurrentUserUseCase = get(),
            ideaRepository = get()
        )
    }

    single<RemoveIdeaUseCase> { RemoveIdeaUseCaseImpl(get()) }

    single<GetCloudMessagingTokenUseCase> {
        GetCloudMessagingTokenUseCaseImpl(
            messagingService = get()
        )
    }
}