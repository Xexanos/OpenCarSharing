package de.librecarsharing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


@Path("api")
@Transactional
public class RestApi {


    @PersistenceContext
    private EntityManager entityManager;

    @Path("carsfromuser/{userId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWithoutRides> getAllCarsFromUser(@PathParam("userId") final long userId) {

        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBCar> query = builder.createQuery(DBCar.class);
        final Root<DBCar> from = query.from(DBCar.class);
        final Join<DBCar,DBUser> join = from.join(DBCar_.owner);
        Predicate predicate = builder.equal(join.get(DBCommunity_.id),userId);
        Order order = builder.asc(from.get(DBCar_.name));
        query.select(from).where(predicate).orderBy(order);
        final List<DBCar> cars = this.entityManager.createQuery(query).getResultList();

        System.out.println("result "+ cars);
        return cars.stream().map(CarWithoutRides::new).collect(Collectors.toList());

    }
    @Path("carsfromcommunity/{communityid}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarWithoutRides> getAllCarsFromCommunity(@PathParam("communityid") final long comId) {

        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBCar> query = builder.createQuery(DBCar.class);
        final Root<DBCar> from = query.from(DBCar.class);
        final Join<DBCar,DBCommunity> join = from.join(DBCar_.community);
        Predicate predicate = builder.equal(join.get(DBCommunity_.id),comId);
        Order order = builder.asc(from.get(DBCar_.name));
        query.select(from).where(predicate).orderBy(order);
        final List<DBCar> cars = this.entityManager.createQuery(query).getResultList();

        System.out.println("result "+ cars);
        return cars.stream().map(CarWithoutRides::new).collect(Collectors.toList());

    }
    @Path("users/{communityid}/")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserNoRef> getAllUsersFromCommunity(@PathParam("communityid") final long comId) {


        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBUser> query = builder.createQuery(DBUser.class);
        final Root<DBUser> from = query.from(DBUser.class);
        final Join<DBUser,DBCommunity> join = from.join(DBUser_.communities);
        Predicate predicate = builder.equal(join.get(DBCommunity_.id),comId);
        Order order = builder.asc(from.get(DBUser_.dispname));
        query.select(from).where(predicate).orderBy(order);
        final List<DBUser> users = this.entityManager.createQuery(query).getResultList();
        System.out.println("result "+ users);
        return users.stream().map(UserNoRef::new).collect(Collectors.toList());

    }
    @Path("communitysformuser/{userid}/")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CommunityNoRef> getAllCommunitysFromUser(@PathParam("userid") final long userId) {


        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBCommunity> query = builder.createQuery(DBCommunity.class);
        final Root<DBCommunity> from = query.from(DBCommunity.class);
        final Join<DBCommunity,DBUser> join = from.join(DBCommunity_.users);
        Predicate predicate = builder.equal(join.get(DBCommunity_.id),userId);
        Order order = builder.asc(from.get(DBCommunity_.name));
        query.select(from).where(predicate).orderBy(order);
        final List<DBCommunity> communitys = this.entityManager.createQuery(query).getResultList();
        System.out.println("result "+ communitys);
        return communitys.stream().map(CommunityNoRef::new).collect(Collectors.toList());
    }
    @Path("allcommunities/")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CommunityNoRef> getAllCommunitys() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBCommunity> query = builder.createQuery(DBCommunity.class);
        final Root<DBCommunity> from = query.from(DBCommunity.class);
        Order order = builder.asc(from.get(DBCommunity_.name));
        query.select(from).orderBy(order);
        final List<DBCommunity> communitys = this.entityManager.createQuery(query).getResultList();
        System.out.println("result "+ communitys);
        return communitys.stream().map(CommunityNoRef::new).collect(Collectors.toList());
    }

    @Path("rides/{carid}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBRide> getAllRidesFromCar(@PathParam("carid") final long carId) {


        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBRide> query = builder.createQuery(DBRide.class);
        final Root<DBRide> from = query.from(DBRide.class);
        final Join<DBRide,DBCar> join = from.join(DBRide_.car);
        Predicate predicate = builder.equal(join.get(DBCar_.id),carId);
        Order order = builder.asc(from.get(DBRide_.id));
        query.select(from).where(predicate).orderBy(order);
        final List<DBRide> rides = this.entityManager.createQuery(query).getResultList();
        System.out.println("result "+ rides);
        return rides ;

    }

    @Path("owner/{carid}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public UserNoRef getOwnerOfCar(@PathParam("carid") final long carId) {


        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<DBUser> query = builder.createQuery(DBUser.class);
        final Root<DBUser> from = query.from(DBUser.class);
        final Join<DBUser,DBCar> join = from.join(DBUser_.cars);
        Predicate predicate = builder.equal(join.get(DBCar_.id),carId);
        query.select(from).where(predicate);
        final DBUser owner = this.entityManager.createQuery(query).getResultList().get(0);
        System.out.println("result "+ owner);
        return new UserNoRef(owner);

    }
    @Path("carwrides/{id}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response carsWithRides(@PathParam("id") final long id) {
        CarWithRides car=new CarWithRides(this.entityManager.find(DBCar.class, id));
        return Response.ok(car).build();
    }

    @Path("ride/{id}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public DBRide getRideById(@PathParam("id") final long id) {
        return this.entityManager.find(DBRide.class, id);
    }

    @Path("car/{id}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public DBRide getCarById    (@PathParam("id") final long id) {
        return this.entityManager.find(DBRide.class, id);
    }

    @Path("community/{id}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public DBCommunity getCommunityById(@PathParam("id") final long id) {
        return this.entityManager.find(DBCommunity.class, id);
    }



    @Path("newuser/{name}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@PathParam("name") final String name) {
        final DBUser user = new DBUser();
        user.setDispname(name);
        this.entityManager.persist(user);
        return Response.ok(user).build();
    }

    @Path("adduser/{userId}/to/{comId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Response create(@PathParam("userId") final long userId, @PathParam("comId") final long comId) {
        DBCommunity community;
        DBUser user;
        if((community=this.entityManager.find(DBCommunity.class,comId ))!=null);
        {
            if((user= this.entityManager.find(DBUser.class,userId))!=null)
            {
                community.addUser(user);
            }
        }


        return Response.ok().build();
    }


}