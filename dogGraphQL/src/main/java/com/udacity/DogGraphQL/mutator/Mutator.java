package com.udacity.DogGraphQL.mutator;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.udacity.DogGraphQL.entity.Dog;
import com.udacity.DogGraphQL.exception.DogNotFoundException;
import com.udacity.DogGraphQL.repository.DogRepository;

import java.util.Optional;

public class Mutator implements GraphQLMutationResolver {
    private DogRepository dogRepository;

    public Mutator(DogRepository dogRepository){
        this.dogRepository = dogRepository;
    }

    public Boolean deleteDogBreed(String breed){
        boolean deleted = false;
        Iterable<Dog> allDogs = dogRepository.findAll();

        for(Dog dog: allDogs){
            if(dog.getBreed().equals(breed)){
                dogRepository.deleteById(dog.getId());
                deleted = true;
            }
        }
        return deleted;
    }

    public Dog updateDogName(String name, Long id){
        Optional<Dog> optionalDog = dogRepository.findById(id);
        if(optionalDog.isPresent()){
            Dog dog = optionalDog.get();
            dog.setName(name);
            dogRepository.save(dog);
            return dog;
        }
        else{
            throw new DogNotFoundException("Dog not Found", id);
        }
    }

}
