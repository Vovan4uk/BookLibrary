package ua.softserve.booklibrary.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.testng.Assert;
import org.testng.annotations.Test;
import ua.softserve.booklibrary.bean.AuthorAction;
import ua.softserve.booklibrary.bean.filter.BooksFilteringBean;
import ua.softserve.booklibrary.bean.sort.MainSortingBean;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.rest.client.AuthorClientService;
import ua.softserve.booklibrary.rest.service.AuthorService;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import java.io.File;

public class AuthorTest extends Arquillian {

	@EJB
	private AuthorHome authorHome;

	@EJB
	private AuthorFacade authorFacade;

	@EJB
	private AuthorManager authorManager;

	@Deployment
	public static Archive<?> createTestArchive() {

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");

		File[] richfacesLibs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies()
				.resolve("org.richfaces:richfaces:4.5.10.Final")
				.withTransitivity().asFile();

		war.addPackages(true, Author.class.getPackage());
		war.addPackages(true, AuthorHome.class.getPackage());
		war.addPackages(true, AuthorFacade.class.getPackage());
		war.addPackages(true, AuthorManager.class.getPackage());
		war.addPackages(true, AuthorAction.class.getPackage());
		war.addPackages(true, AuthorClientService.class.getPackage());
		war.addPackages(true, AuthorService.class.getPackage());
		war.addClass(MainSortingBean.class);
		war.addClass(BooksFilteringBean.class);
		war.addClass(LibraryException.class);
		war.addAsLibraries(richfacesLibs);
		war.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		return war;
	}

	/*Simple DAO test*/
	@Test
	public void authorDAOTest(){
		//Create new Author
		Author author = new Author();
		author.setFirstName("TestName");

		//Persist new Author
		authorHome.save(author);

		//Check id after persist
		Assert.assertNotNull(author.getId());

		//Create another Author
		Author anotherAuthor = new Author();
		anotherAuthor.setFirstName("TestAnotherName");

		//Persist another Author
		authorHome.save(anotherAuthor);

		//Check count Authors in database
		Assert.assertEquals(2, authorFacade.findAll().size());

		//Change first name
		author.setFirstName("ChangedName");

		//Update Author
		authorHome.update(author);

		//Check if update successful
		Assert.assertEquals("ChangedName", authorFacade.findByPk(author.getId()).getFirstName());

		//Delete Author
		authorHome.removeByPk(author.getId());

		//Check count Authors in database
		Assert.assertEquals(1, authorFacade.findAll().size());
	}

	/*Test save Author with Arquillian persistence DBUnit through Manager(business level)*/
	@Test
	@UsingDataSet("datasets/initial-authors-dataset.xml")
	@ShouldMatchDataSet(value = "datasets/after-save-authors-dataset.xml",
			excludeColumns = {"ID", "CREATE_DATE"},
			orderBy = {"FIRST_NAME"})
	public void saveAuthorTest() throws Exception {
		Author author = new Author();
		author.setFirstName("Monk");
		author.setSecondName("Simon");
		authorManager.save(author);
	}

	/*Test update Author with Arquillian persistence DBUnit through Manager(business level)*/
	@Test
	@UsingDataSet("datasets/initial-authors-dataset.xml")
	@ShouldMatchDataSet(value = "datasets/after-update-authors-dataset.xml",
			excludeColumns = {"ID", "CREATE_DATE"},
			orderBy = {"FIRST_NAME"})
	public void updateAuthorTest() throws Exception {
		Author author = authorManager.findByPk(1000L);
		author.setFirstName("Lafferty");
		author.setSecondName("Mur");
		authorManager.update(author);
	}

	/*Test delete Author with Arquillian persistence DBUnit through Manager(business level)*/
	@Test
	@UsingDataSet("datasets/initial-authors-dataset.xml")
	@ShouldMatchDataSet(value = "datasets/after-delete-authors-dataset.xml",
			excludeColumns = {"ID", "CREATE_DATE"},
			orderBy = {"FIRST_NAME"})
	public void deleteAuthorTest() throws Exception {
		authorManager.removeByPk(1000L);
	}

	/*Test save not unique Author with Arquillian persistence DBUnit through Manager(business level)*/
	@Test(expectedExceptions = EJBException.class)
	@UsingDataSet("datasets/initial-authors-dataset.xml")
	public void saveNotUniqueAuthorTest() throws Exception {
		Author author = new Author();
		author.setFirstName("Lafore");
		author.setSecondName("Robert");
		authorManager.save(author);
	}
}