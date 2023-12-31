package com.fitplanner.workout.service;

import com.fitplanner.workout.client.UserServiceClient;
import com.fitplanner.workout.model.api.ConfirmationResponse;
import com.fitplanner.workout.model.api.ExerciseRequest;
import com.fitplanner.workout.model.training.*;
import com.fitplanner.workout.model.training.exercise.Exercise;
import com.fitplanner.workout.model.training.exercise.ExerciseType;
import com.fitplanner.workout.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class WorkoutService {

    private final ExerciseRepository exerciseRepository;
    private final UserServiceClient userServiceClient;

    @Autowired
    public WorkoutService(ExerciseRepository exerciseRepository, UserServiceClient userServiceClient) {
        this.exerciseRepository = exerciseRepository;
        this.userServiceClient = userServiceClient;
    }

    public ConfirmationResponse addUserStrengthExercise(String email, String date, UserStrengthExercise strengthExercise,
        String header
    ) {
        var user = userServiceClient.getUser(email, header);

        var workoutPlan = user.getWorkoutPlanList().stream()
            .filter(plan -> plan.getDate().equals(date))
            .findFirst()
            .orElseGet(() -> {
               var newPlan = new WorkoutPlan(date);
               user.getWorkoutPlanList().add(newPlan);
               return newPlan;
            });

        workoutPlan.getStrengthExerciseList().add(strengthExercise);
        userServiceClient.saveUserWorkoutPlanList(email, user.getWorkoutPlanList(), header);

        return new ConfirmationResponse("Exercise has been added.");
    }

    public ConfirmationResponse removeUserStrengthExercise(String email, String date, String strengthExerciseId,
        String header
    ) {
        var user = userServiceClient.getUser(email, header);

        var userWorkoutPlan = user.getWorkoutPlanList().stream()
            .filter(plan -> plan.getDate().equals(date))
            .findFirst();

        userWorkoutPlan.ifPresent(workoutPlan -> {
            workoutPlan.getStrengthExerciseList().removeIf(strengthExercise -> strengthExercise.getId().equals(strengthExerciseId));

            var hasNoOtherExercises = user.getWorkoutPlanList().stream()
                .filter(plan -> plan.getDate().equals(date))
                .anyMatch(plan -> plan.getStrengthExerciseList().isEmpty() && plan.getCardioExerciseList().isEmpty());

            if(hasNoOtherExercises)
                user.getWorkoutPlanList().removeIf(plan -> plan.getDate().equals(date));
        });

        userServiceClient.saveUserWorkoutPlanList(email, user.getWorkoutPlanList(), header);

        return new ConfirmationResponse("Exercise has been removed.");
    }

    public ConfirmationResponse addUserCardioExercise(String email, String date, UserCardioExercise cardioExercise,
        String header
    ) {
        var user = userServiceClient.getUser(email, header);

        var workoutPlan = user.getWorkoutPlanList().stream()
            .filter(plan -> plan.getDate().equals(date))
            .findFirst()
            .orElseGet(() -> {
                var newPlan = new WorkoutPlan(date);
                user.getWorkoutPlanList().add(newPlan);
                return newPlan;
            });

        workoutPlan.getCardioExerciseList().add(cardioExercise);
        userServiceClient.saveUserWorkoutPlanList(email, user.getWorkoutPlanList(), header);

        return new ConfirmationResponse("Exercise has been added.");
    }

    public ConfirmationResponse removeUserCardioExercise(String email, String date, String cardioExerciseId,
        String header
    ) {
        var user = userServiceClient.getUser(email, header);

        var userWorkoutPlan = user.getWorkoutPlanList().stream()
            .filter(plan -> plan.getDate().equals(date))
            .findFirst();

        userWorkoutPlan.ifPresent(workoutPlan -> {
            workoutPlan.getCardioExerciseList().removeIf(cardioExercise -> cardioExercise.getId().equals(cardioExerciseId));

            var hasNoOtherExercises = user.getWorkoutPlanList().stream()
                .filter(plan -> plan.getDate().equals(date))
                .anyMatch(plan -> plan.getCardioExerciseList().isEmpty() && plan.getStrengthExerciseList().isEmpty());

            if(hasNoOtherExercises)
                user.getWorkoutPlanList().removeIf(plan -> plan.getDate().equals(date));
        });

        userServiceClient.saveUserWorkoutPlanList(email, user.getWorkoutPlanList(), header);

        return new ConfirmationResponse("Exercise has been removed.");
    }

    public WorkoutPlan getWorkoutPlan(String email, String date, String header) {
        var user = userServiceClient.getUser(email, header);

        return user.getWorkoutPlanList().stream()
            .filter(plan -> plan.getDate().equals(date))
            .findFirst()
            .orElse(new WorkoutPlan(date));
    }

    public List<Exercise> getExercisesByName(String name, ExerciseType type) {
        if(name.isEmpty() || type == null)
            return Collections.emptyList();

        return exerciseRepository.findByNameAndExerciseTypeIgnoreCase(name, type)
            .orElse(Collections.emptyList());
    }

    public List<Exercise> getExercisesByMuscle(String muscle) {
        if(muscle.isEmpty())
            return Collections.emptyList();

        return exerciseRepository.findByMuscleIgnoreCase(muscle)
            .orElse(Collections.emptyList());
    }

    public void addExercise(ExerciseRequest request) {
        var exercise = new Exercise(request.name(), request.link(), request.muscle(), request.type());
        exerciseRepository.save(exercise);
    }
}
