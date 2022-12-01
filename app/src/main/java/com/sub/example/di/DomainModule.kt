package com.sub.example.di

import com.sub.data.repository.AppsflyerRepositoryImpl
import com.sub.data.repository.CodeRepositoryImpl
import com.sub.data.repository.FileRepositoryImpl
import com.sub.domain.repository.FileRepository
import com.sub.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideGetFlowUseCase(appsflyerRepository: AppsflyerRepositoryImpl) = GetFlowUseCase(repository = appsflyerRepository)

    @Provides
    fun provideGetCodeUseCase(codeRepository: CodeRepositoryImpl) = GetCodeUseCase(repository = codeRepository)

    @Provides
    fun provideGetInitUseCase(appsflyerRepository: AppsflyerRepositoryImpl) = GetInitUseCase(repository = appsflyerRepository)

    @Provides
    fun provideCreateFileUseCase(fileRepository: FileRepositoryImpl) = CreateFileUseCase(repository = fileRepository)

    @Provides
    fun provideSendFileUseCase(fileRepository: FileRepositoryImpl) = SendFileUseCase(repository = fileRepository)
}