package com.example.e4.rcp.todo.rest.services.internal;

import java.util.List;

import com.example.e4.rcp.todo.model.Todo;

import reactor.core.publisher.Mono;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TodoRestRequest {
	@GET
	("/v2/5c0149563500005c00ad08f5")
	Call<List<Todo>> getTodos();

	@GET
	("/v2/5c0149563500005c00ad08f5")
	Mono<List<Todo>> getReactorTodos();
}
