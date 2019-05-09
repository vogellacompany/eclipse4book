package com.vogella.tasks.services.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vogella.tasks.model.ITodoService;
import com.vogella.tasks.model.Todo;

@Component
public class MyTodoServiceImpl implements ITodoService {

	String fileName = "dataFile";

	private static AtomicInteger current = new AtomicInteger(1);
	private List<Todo> todos;

	public MyTodoServiceImpl() {
		todos = createInitialModel();
	}

	@Override
	public void getTodos(Consumer<List<Todo>> todosConsumer) {
		// always pass a new copy of the data
		todosConsumer.accept(todos.stream().map(t -> t.copy()).collect(Collectors.toList()));
	}

	protected List<Todo> getTodosInternal() {
		return todos;
	}

	// create or update an existing instance of a todo object
	@Override
	public synchronized boolean saveTodo(Todo newTodo) {
		// hold the Optional object as reference to determine, if the Todo is
		// newly created or not
		Optional<Todo> todoOptional = findById(newTodo.getId());

		// get the actual task object or create a new one
		Todo todo = todoOptional.orElse(new Todo(current.getAndIncrement()));
		todo.setSummary(newTodo.getSummary());
		todo.setDescription(newTodo.getDescription());
		todo.setDone(newTodo.isDone());
		todo.setDueDate(newTodo.getDueDate());

		if (!todoOptional.isPresent()) {
			todos.add(todo);
		}

		// Save data as new JSON
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<List<Todo>>() {
		}.getType();
		String json = gson.toJson(todos, type);
		try {
			writeToTextFile(fileName, json);
		} catch (IOException e) {
			// ignore
		}
		return true;
	}

	@Override
	public Optional<Todo> getTodo(long id) {
		return findById(id).map(todo -> todo.copy());
	}

	@Override
	public boolean deleteTodo(long id) {
		Optional<Todo> deleteTodo = findById(id);

		deleteTodo.ifPresent(todo -> {
			todos.remove(todo);
		});

		return deleteTodo.isPresent();
	}

	// Example data, change if you like
	private List<Todo> createInitialModel() {
		List<Todo> list = new ArrayList<>();

		try {
			List<String> readTextFileByLines = readTextFileByLines(fileName);
			if (readTextFileByLines != null) {
				Gson gson = new Gson();
				java.lang.reflect.Type type = new TypeToken<List<Todo>>() {}.getType();
				String string =  readTextFileByLines.stream().map(String::valueOf).collect(Collectors.joining());
				// Convert back to Java
				List<Todo> fromJson = gson.fromJson(string, type);
				list.addAll(fromJson);
			} else {

				// Create some initial content
				list.add(createTodo("Application model", "Flexible and extensible"));
				list.add(createTodo("DI", "@Inject as programming mode"));
				list.add(createTodo("OSGi", "Services"));
				list.add(createTodo("SWT", "Widgets"));
				list.add(createTodo("JFace", "Especially Viewers!"));
				list.add(createTodo("CSS Styling", "Style your application"));
				list.add(createTodo("Eclipse services", "Selection, model, Part"));
				list.add(createTodo("Renderer", "Different UI toolkit"));
				list.add(createTodo("Compatibility Layer", "Run Eclipse 3.x"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static List<String> readTextFileByLines(String fileName) throws IOException {
		if (!new File(fileName).exists()) {
			return Collections.emptyList();
		}
		return Files.readAllLines(Paths.get(fileName));
	}

	public static void writeToTextFile(String fileName, String content) throws IOException {
		Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
	}

	private Todo createTodo(String summary, String description) {
		return new Todo(current.getAndIncrement(), summary, description, false, new Date());
	}

	private Optional<Todo> findById(long id) {
		return getTodosInternal().stream().filter(t -> t.getId() == id).findAny();
	}



}