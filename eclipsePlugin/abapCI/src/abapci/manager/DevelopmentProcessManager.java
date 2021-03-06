package abapci.manager;

import abapci.domain.SourcecodeState;
import abapci.domain.TestState;

public class DevelopmentProcessManager {

	private TestState unitTestState;
	private TestState atcTestState;

	public DevelopmentProcessManager() {
		unitTestState = TestState.UNDEF;
		atcTestState = TestState.UNDEF;
	}

	public void setUnitTeststate(TestState state) {
		this.unitTestState = state;
	}

	public void setAtcTeststate(TestState state) {
		this.atcTestState = state;
	}

	public SourcecodeState getSourcecodeState() {
		SourcecodeState state;

		if (unitTestState == null)
			unitTestState = TestState.UNDEF;

		switch (unitTestState) {
		case NOK:
			state = SourcecodeState.UT_FAIL;
			break;
		case OK:
			state = atcTestState == TestState.NOK ? SourcecodeState.ATC_FAIL : SourcecodeState.OK;
			break;
		case OFFL:
			state = SourcecodeState.OFFL;
			break;
		case DEACT:
			state = atcTestState == TestState.NOK ? SourcecodeState.ATC_FAIL : SourcecodeState.OK;
			break;
		case UNDEF:
		default:
			state = SourcecodeState.UNDEF;
			break;
		}

		return state;
	}
}
