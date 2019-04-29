package ch.uzh.ifi.ase.csccrecommender.index;

import java.util.List;

public class Invocation {
	// TODO: tokensBeforeInvocation can represent overall context, but we also need linecontext, 
	// 	which should be separated from tokensBeforeInvocation.
  private List<String> tokensBeforeInvocation;
  private String type;
  private String methodName;
  	// TODO: create simhash(specifically, Jenkin hash function) for each document(invocation)

  public Invocation(List<String> tokensBeforeInvocation, String type, String methodName) {
    this.tokensBeforeInvocation = tokensBeforeInvocation;
    this.type = type;
    this.methodName = methodName;
  }

  public List<String> getTokensBeforeInvocation() {
    return tokensBeforeInvocation;
  }

  public String getType() {
    return type;
  }

  public String getMethodName() {
    return methodName;
  }
}
