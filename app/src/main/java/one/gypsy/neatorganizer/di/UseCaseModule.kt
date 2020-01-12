package one.gypsy.neatorganizer.di

import dagger.Module
import dagger.Provides
import one.gypsy.neatorganizer.data.FileRepository
import one.gypsy.neatorganizer.data.PeopleRepository
import one.gypsy.neatorganizer.interactors.AddPerson
import one.gypsy.neatorganizer.interactors.GetAllPeople
import one.gypsy.neatorganizer.interactors.GetImageBitmap

@Module
class UseCaseModule {

    @Provides
    fun provideAddPersonUseCase(peopleRepository: PeopleRepository): AddPerson {
        return AddPerson(peopleRepository)
    }

    @Provides
    fun provideGetAllPeopleUseCase(peopleRepository: PeopleRepository): GetAllPeople {
        return GetAllPeople(peopleRepository)
    }

    @Provides
    fun provideGetImageBitmapUseCase(fileRepository: FileRepository): GetImageBitmap {
        return GetImageBitmap(fileRepository)
    }
}