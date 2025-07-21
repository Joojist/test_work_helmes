package com.example.test_work_helmes.service;

import com.example.test_work_helmes.Repository.SectorRepository;
import com.example.test_work_helmes.Repository.UserRepository;
import com.example.test_work_helmes.entity.Sector;
import com.example.test_work_helmes.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final SectorRepository sectorRepo;

    public UserService(UserRepository userRepo, SectorRepository sectorRepo) {
        this.userRepo = userRepo;
        this.sectorRepo = sectorRepo;
    }

    // find user by ID
    public Optional<User> findUser(Long userId) {
        return userRepo.findById(userId);
    }

    // save or update user with sectors
    public User saveUser(User user, List<Long> sectorIds) {
        List<Sector> sectors = sectorRepo.findAllById(sectorIds);
        user.setSectors(new HashSet<>(sectors));
        return userRepo.save(user);
    }

    // remove a sector from a user
    @Transactional
    public void removeSector(Long userId, Long sectorId) {
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean removed = user.getSectors().removeIf(sector -> sector.getId().equals(sectorId));
            System.out.println("Was sector removed? " + removed);
            userRepo.save(user);
        }
    }

    // get all sectors
    public List<Sector> getSectors() {
        return sectorRepo.findAll();
    }

    // get sectors by list of IDs
    public List<Sector> getSectorsByIds(List<Long> sectorIds) {
        return sectorRepo.findAllById(sectorIds);
    }

    // delete user by ID
    @Transactional
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }
}
