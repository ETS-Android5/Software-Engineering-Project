package comp3350.breadtunes.application;

import comp3350.breadtunes.persistence.CoursePersistence;
import comp3350.breadtunes.persistence.SCPersistence;
import comp3350.breadtunes.persistence.StudentPersistence;
import comp3350.breadtunes.persistence.stubs.CoursePersistenceStub;
import comp3350.breadtunes.persistence.stubs.SCPersistenceStub;
import comp3350.breadtunes.persistence.stubs.StudentPersistenceStub;

public class Services
{
	private static StudentPersistence studentPersistence = null;
	private static CoursePersistence coursePersistence = null;
	private static SCPersistence scPersistence = null;

	public static synchronized StudentPersistence getStudentPersistence()
    {
		if (studentPersistence == null)
		{
		    studentPersistence = new StudentPersistenceStub();
        }

        return studentPersistence;
	}

    public static synchronized CoursePersistence getCoursePersistence()
    {
        if (coursePersistence == null)
        {
            coursePersistence = new CoursePersistenceStub();
        }

        return coursePersistence;
    }

	public static synchronized SCPersistence getScPersistence() {
        if (scPersistence == null) {
            scPersistence = new SCPersistenceStub();
        }

        return scPersistence;
    }
}
