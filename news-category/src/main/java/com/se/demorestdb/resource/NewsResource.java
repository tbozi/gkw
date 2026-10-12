package com.se.demorestdb.resource;

import com.se.demorestdb.dto.CatogoryDTO;
import com.se.demorestdb.model.TinTuc;
import com.se.demorestdb.service.TinTucService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/news")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsResource {
    @Inject
    private TinTucService tinTucService;

    @GET
    public Response getAll(){
        List<TinTuc> list = tinTucService.getAll();
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response findOne(@PathParam("id") int id){
        TinTuc tinTuc = tinTucService.getOne(id);
        return Response.ok(tinTuc).build();
    }

    @POST
    public Response insert(TinTuc tinTuc){
        TinTuc tinTuc1 = tinTucService.insert(tinTuc);
        return Response.status(Response.Status.CREATED).entity(tinTuc1).build();
    }

    @PUT
    @Path("{id}")
    public Response insert(@PathParam("id") int id, TinTuc tinTuc){
        boolean update = tinTucService.update(tinTuc,id);
        return Response.ok(update).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id){
        boolean delete = tinTucService.delete(id);
        return Response.ok(delete).build();
    }

    @GET
    @Path("/by-category/{categoryId}")
    public Response findByCategory(@PathParam("categoryId") int categoryId){
        List<CatogoryDTO> list = tinTucService.findByCategory(categoryId);
        return Response.ok(list).build();
    }

    @GET
    @Path("/by-category-name/{categoryName}")
    public Response getNewsOfCategoryName(@PathParam("categoryName") String categoryName){
        List<TinTuc> list = tinTucService.getNewsOfCategoryName(categoryName);
        return Response.ok(list).build();
    }

    @GET
    @Path("/count-by-category")
    public Response countNewsOfCategory(){
        return Response.ok(tinTucService.countNewsOfCategory()).build();
    }

    @GET
    @Path("/count-by-news")
    public Response getCategoryCountNews(){
        return Response.ok(tinTucService.getCategoryCountNews()).build();
    }

    @GET
    @Path("/max-news-category")
    public Response getMaxNewsCategory(){
        return Response.ok(tinTucService.getMaxNewsCategory()).build();
    }
}
