package me.gwma.tools.protobuf;

import com.protobuftest.protobuf.PersonProbuf.Person;

public class ProtoBufTest {

	public static void main(String[] args) {
		Person john = Person.newBuilder().setId(1234).setName("John Doe").setEmail("jdoe@example.com")
				.addPhone(Person.PhoneNumber.newBuilder().setNumber("555-4321").setType(Person.PhoneType.HOME)).build();
		System.out.println("Begin...");
	}

}
