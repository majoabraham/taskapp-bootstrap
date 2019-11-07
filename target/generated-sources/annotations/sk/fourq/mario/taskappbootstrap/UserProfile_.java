package sk.fourq.mario.taskappbootstrap;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sk.fourq.bootstrap.domain.User;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserProfile.class)
public abstract class UserProfile_ {

	public static volatile SingularAttribute<UserProfile, Integer> id;
	public static volatile SingularAttribute<UserProfile, User> user;
	public static volatile SingularAttribute<UserProfile, String> customField;

}

