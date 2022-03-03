package org.tarent.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController {
	
	@Autowired
	TrainingRepository repository;
	
	@PostMapping("/training")
    public void date(@RequestBody Training training) {
        repository.save(training);
    }
}
