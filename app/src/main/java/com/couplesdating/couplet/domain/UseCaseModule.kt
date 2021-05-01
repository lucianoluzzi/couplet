package com.couplesdating.couplet.domain

import com.couplesdating.couplet.domain.useCase.auth.*
import com.couplesdating.couplet.domain.useCase.category.GetCategoriesUseCase
import com.couplesdating.couplet.domain.useCase.category.GetCategoriesUseCaseImpl
import com.couplesdating.couplet.domain.useCase.invite.*
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

    single<GetInviteUseCase> { GetAInviteUseCaseImpl(get()) }

    single<DeleteInviteUseCase> { DeleteInviteUseCaseImpl(get()) }

    single<FormPairUseCase> { FormPairUseCaseImpl(get(), get()) }

    single<CreateInviteUseCase> { CreateInviteUseCaseImpl(get()) }

    single<ShouldShowSyncUseCase> { ShouldShowSyncUseCaseImpl(get()) }

    single<SetSyncShownUseCase> { SetSyncShownUseCaseImpl(get()) }

    single<GetPartnerUseCase> { GetPartnerUseCaseImpl(get()) }

    single<InviteExistsUseCase> { InviteExistsUseCaseImpl(get()) }

    single<GetCategoriesUseCase> { GetCategoriesUseCaseImpl(get()) }

    single<AddInviteeIdUseCase> { AddInviteeIdUseCaseImpl(get()) }
}