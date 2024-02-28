package co.develhope.crudesx.controllers;

import co.develhope.crudesx.entities.Car;
import co.develhope.crudesx.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("car")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @PostMapping("/create/car")
    public Car createOneCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @GetMapping("find/allcars")
    public List<Car> findAllCars() {
        List<Car> getAllCars = carRepository.findAll();
        return getAllCars;
    }

    @GetMapping("{id}")
    public Car findCarById(@PathVariable Long id) {
        return carRepository.findById(id).orElse(new Car());
    }

    @PostMapping("/modify/type/{id}")
    public Car modifyCarType(@PathVariable Long id, @RequestBody Car car) {
        Optional<Car> carVar = carRepository.findById(id);
        if (carVar.isPresent()) {
            Car foundCar = carVar.get();
            foundCar.setType(car.getType());
            return carRepository.saveAndFlush(foundCar);
        } else {
            return new Car();
        }
    }

    @DeleteMapping("/delete/car")
    public ResponseEntity<String> deleteOneCar(@RequestBody Car car) {
        Optional<Car> carVar = carRepository.findById(car.getId());
        if (car == null || car.getId() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("missing body");
        }
        if (carVar.isPresent()) {
            carRepository.delete(car);
            return ResponseEntity.ok("Car deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Car not found");
        }
    }

    @DeleteMapping("/deleteall")
    public void deleteAllCars() {
        carRepository.deleteAll();
    }
}