package com.example.nplanner.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.nplanner.model.Schedule;
import com.example.nplanner.model.Task;
import com.example.nplanner.service.FirebaseService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

@Controller
public class GeneralController {

	@Autowired
	FirebaseService db;
	
	@GetMapping("/")
	public String main(){
		
		return "main";
	}

	@GetMapping("/addTask")
	public String addTask(Model model) throws InterruptedException, ExecutionException {
		CollectionReference tsk_collection = db.getFirebase().collection("Task");
		CollectionReference schdle_collection = db.getFirebase().collection("Schedule");
		ApiFuture<QuerySnapshot> qs_tsk = tsk_collection.get();
		ApiFuture<QuerySnapshot> qs_schdle = schdle_collection.get();
		List<Task> tList = new ArrayList<Task>();
		List<Schedule> sList = new ArrayList<Schedule>();
		for(DocumentSnapshot doc:qs_tsk.get().getDocuments()) {
			Task tsk = doc.toObject(Task.class);
			tList.add(tsk);
		}
		for(DocumentSnapshot doc:qs_schdle.get().getDocuments()) {
			Schedule schdle = doc.toObject(Schedule.class);
			sList.add(schdle);
		}
		model.addAttribute("task_list", tList);
		model.addAttribute("schedule_list", sList);
		return "/task/addtask";
	}
	
	@GetMapping("/saveTask")
	public String saveTask(@ModelAttribute Task task,
			@RequestParam(name = "task_string") String task_string,
			@RequestParam(name = "description", defaultValue = "") String description,
			@RequestParam(name = "success", defaultValue = "false") Boolean success) throws InterruptedException, ExecutionException {
		
		CollectionReference tsk_collection = db.getFirebase().collection("Task");
		ApiFuture<QuerySnapshot> qs = tsk_collection.get();
		Integer task_id = 1;
		for(DocumentSnapshot doc:qs.get().getDocuments()) {
			task_id++;
		}
		System.out.println(task_id);
		task.setTask_id(task_id);
		task.setTask(task_string);
		task.setDescription(description);
		task.setSuccess(success);
		tsk_collection.document(String.valueOf(task.getTask_id())).set(task);
		return "redirect:/";
	}
	
	@GetMapping("/saveSchedule")
	public String saveSchedule(@ModelAttribute Task task,
			@ModelAttribute Schedule schedule,
			@RequestParam(name = "date") String date,
			@RequestParam(name = "time") String time,
			@RequestParam(name = "task_string") String task_string,
			@RequestParam(name = "description", defaultValue = "") String description) throws InterruptedException, ExecutionException {
		
		CollectionReference tsk_collection = db.getFirebase().collection("Task");
		CollectionReference schdle_collection = db.getFirebase().collection("Schedule");
		ApiFuture<QuerySnapshot> qs_tsk = tsk_collection.get();
		ApiFuture<QuerySnapshot> qs_schdle = schdle_collection.get();
		Integer task_id = 1, schedule_id = 1;
		
		for(DocumentSnapshot doc:qs_tsk.get().getDocuments()) {
			task_id++;
		}
		for(DocumentSnapshot doc:qs_schdle.get().getDocuments()) {
			schedule_id++;
		}
		
		schedule.setSchedule_id(schedule_id);
		schedule.setTask_id(task_id);
		System.out.println(date +"\n"+ time);
		schedule.setDate(date);
		schedule.setTime(time);
		schdle_collection.document(String.valueOf(schedule.getSchedule_id())).set(schedule);
		
		task.setTask_id(task_id);
		task.setTask(task_string);
		task.setDescription(description);
		task.setSuccess(false);
		tsk_collection.document(String.valueOf(task.getTask_id())).set(task);
		return "redirect:/addTask";
	}
	
	@GetMapping("/deletetsk/{task_id}")
	public String deleteTask(@PathVariable("task_id") String task_id) throws InterruptedException, ExecutionException {
		db.getFirebase().collection("Task").document(task_id).delete();
		return "redirect:/";
	}
	@GetMapping("/deleteschdle/{schedule_id}")
	public String deleteSchedule(@PathVariable("schedule_id") String schedule_id) throws InterruptedException, ExecutionException {
		db.getFirebase().collection("Schedule").document(schedule_id).delete();
		return "redirect:/";
	}
}

@RestController
class TaskController{
	@Autowired
	FirebaseService db;
	
	@GetMapping("/getAllTasks")
	public List<Task> getAllTasks() throws InterruptedException, ExecutionException {
		List<Task> tList = new ArrayList<Task>();
		CollectionReference tsk_collection = db.getFirebase().collection("Task");
		ApiFuture<QuerySnapshot> qs = tsk_collection.get();
		for(DocumentSnapshot doc:qs.get().getDocuments()) {
			Task tsk = doc.toObject(Task.class);
			tList.add(tsk);
		}
		return tList;
	}
	
	@GetMapping("/getTask/{id}")
	public List<Task> getTask(@PathVariable("id") int id) throws InterruptedException, ExecutionException {
		CollectionReference tsk_collection = db.getFirebase().collection("Task");
		Query query = tsk_collection.whereEqualTo("task_id", id);
		ApiFuture<QuerySnapshot> qs = query.get();
		List<Task> tList = new ArrayList<Task>();
		for(DocumentSnapshot doc:qs.get().getDocuments()) {
			Task tsk = doc.toObject(Task.class);
			tList.add(tsk);
		}
		return tList;
	}
	
	
}