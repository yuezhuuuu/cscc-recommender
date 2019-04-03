package ch.uzh.ifi.ase.csccrecommender;

import com.google.inject.Guice;

import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.naming.Names;
import cc.kave.commons.model.naming.codeelements.IMethodName;
import cc.kave.commons.model.naming.codeelements.IParameterName;
import cc.kave.commons.model.naming.types.ITypeName;
import cc.kave.commons.model.ssts.ISST;
import cc.kave.commons.model.ssts.IStatement;
import cc.kave.commons.model.ssts.declarations.IMethodDeclaration;
import cc.kave.commons.model.ssts.impl.visitor.AbstractTraversingNodeVisitor;
import cc.kave.commons.utils.io.IReadingArchive;
import cc.kave.commons.utils.io.ReadingArchive;
import cc.kave.users.examples.IoHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  public static void main(String[] args) {
    // Initialize Guice
    Guice.createInjector(new ProductionModule());

    Logger logger = LoggerFactory.getLogger(Main.class);
    logger.info("Recommender launched!");
    
    
    // a small program to check sst
    String ctxsDir = "/Users/dingguangjin/Desktop/Advanced Software Engineering/existed/java-cc-kave-examples-master/Contexts-170503/zpublic";
	
	Set<String> slnZips = IoHelper.findAllZips(ctxsDir);
	ArrayList<String> list = new ArrayList<String>();
	list.addAll(slnZips);

	try (IReadingArchive ra = new ReadingArchive(new File(ctxsDir, list.get(0)))) {
		// ... and iterate over content.
		int numProcessedContexts = 0;
		// the iteration will stop after 10 contexts to speed things up in the example.
		while (ra.hasNext() && (numProcessedContexts++ < 1)) {
			/*
			 * within the slnZip, each stored context is contained as a single file that
			 * contains the Json representation of a {@see Context}.
			 */
			Context ctx = ra.getNext(Context.class);

			// the events can then be processed individually
			process(ctx.getSST());
		}
	}
  }
  
  // below methods are copied from cc.kave.users.examples
  private static void process(ISST sst) {
		// SSTs represent a simplified meta model for source code. You can use the
		// various accessors to browse the contained information

		// which type was edited?
  	System.out.println(sst.toString());
		ITypeName declType = sst.getEnclosingType();
		
		System.out.println(declType);
		
		System.out.println("Methods:");
		// which methods are defined?
		for (IMethodDeclaration md : sst.getMethods()) {
			IMethodName m = md.getName();
			System.out.println(m);
			for (IStatement stmt : md.getBody()) {
				// process the body, most likely by traversing statements with an {@see
				// ISSTNodeVisitor}
				//stmt.accept(new ExampleVisitor(), null);
			}
		}

		// all references to types or type elements are fully qualified and preserve
		// many information about the resolved type
		declType.getNamespace();
		declType.isInterfaceType();
		declType.getAssembly();

		// you can distinguish reused types from types defined in a local project
		boolean isLocal = declType.getAssembly().isLocalProject();

		// the same is possible for all other <see>IName</see> subclasses, e.g.,
		// <see>IMethodName</see>
		IMethodName m = Names.getUnknownMethod();
		m.getDeclaringType();
		m.getReturnType();
		// or inspect the signature
		for (IParameterName p : m.getParameters()) {
			String pid = p.getName();
			ITypeName ptype = p.getValueType();
		}
	}
  
  private class ExampleVisitor extends AbstractTraversingNodeVisitor<Object, Object> {
		// empty implementation for the example, in reality, you will either reuse
		// existing {@see ISSTNodeVisitor} or build your own subclass.
	}
}
