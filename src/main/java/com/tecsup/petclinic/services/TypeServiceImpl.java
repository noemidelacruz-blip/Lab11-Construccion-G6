package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.dtos.TypePetCountDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mappers.TypeMapper;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.repositories.TypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

	private final TypeRepository typeRepository;
	private final TypeMapper typeMapper;
	private final PetRepository petRepository;

	@Override
	public TypeDTO create(TypeDTO typeDTO) {
		Type newType = typeRepository.save(typeMapper.mapToEntity(typeDTO));
		return typeMapper.mapToDto(newType);
	}

	@Override
	public TypeDTO update(TypeDTO typeDTO) {
		Type updatedType = typeRepository.save(typeMapper.mapToEntity(typeDTO));
		return typeMapper.mapToDto(updatedType);
	}

	@Override
	public void delete(Integer id) throws TypeNotFoundException {
		TypeDTO type = findById(id);
		typeRepository.delete(typeMapper.mapToEntity(type));
	}

	@Override
	public TypeDTO findById(Integer id) throws TypeNotFoundException {
		Optional<Type> type = typeRepository.findById(id);
		if (type.isEmpty())
			throw new TypeNotFoundException("Type not found...!");
		return typeMapper.mapToDto(type.get());
	}

	@Override
	public List<TypeDTO> findAll() {
		return typeRepository.findAll()
				.stream()
				.map(typeMapper::mapToDto)
				.toList();
	}

	@Override
	public List<TypePetCountDTO> getPetCountByType() {
		return typeRepository.findAll()
				.stream()
				.filter(type -> Boolean.TRUE.equals(type.getActive()))
				.map(type -> new TypePetCountDTO(
						type.getId(),
						type.getName(),
						petRepository.countByTypeId(type.getId())))
				.toList();
	}
}
