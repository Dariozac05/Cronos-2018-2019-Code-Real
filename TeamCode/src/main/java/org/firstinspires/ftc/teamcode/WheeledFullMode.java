package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp", group="Cronos")
//@Disabled
public class WheeledFullMode extends WheeledBotHardware {

    float driveMagnitude = 1f; //0.4f  // default drive magnitude

    @Override
    public void start() {

        // do what our parent says first
        super.start();

        //Set drive mode
        setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //Set drive mode
        setElevatorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("joy", String.format("%.2f %.2f", 0.0, 0.0));
        telemetry.addData("pos", String.format("x:%4.0f y:%4.0f h:%3.0f", positionX, positionY, Math.toDegrees(heading)));
        //telemetry.addData("rot", String.format("p:%3.0f r:%3.0f h:%3.0f", orientation.getPitch(), orientation.getRoll(), orientation.getHeading()));
    }

    @Override
    public void loop() {

        // do what our parent says first
        super.loop();

        //Get the values from the gamepads
        //Note: pushing the stick all the way up returns -1,
        // so we need to reverse the y values
        double forward = -gamepad1.left_stick_y;
        double right   = -gamepad1.right_stick_x;
        double turn    =  gamepad1.left_stick_x;
        double lift    = -gamepad2.left_stick_y;
        double SlideOut = gamepad2.right_stick_y;
        double SlideLift = gamepad2.right_stick_x;
        double SlidePower = SlideOut;
        double SlideLiftPower = SlideLift;
        SlidePower = Range.clip(SlidePower, -1.0, 1.0);
        SlideLiftPower = Range.clip(SlideLiftPower, -0.5, 0.5);
        LinSlideMotor.setPower(SlidePower);
        LinSlideUpDown.setPower(SlideLiftPower);

      
        forward = (float) scaleInput(forward);
        right   = (float) scaleInput(right);
        turn    = (float) scaleInput(turn);
        lift    = (float) scaleInput(lift);

      

        //Set the power of the elevator
        if ( Math.abs(lift) > 0.01f)
            moveElevator(lift);
        else
            stopElevator();

        //Set the power of the motors with the gamepad values
        setDrivePower(driveMagnitude * forward, driveMagnitude * right, turn);

        //telemetry.addData("relic", String.format("%.2f", relic.getPosition()));
        telemetry.addData("elev", "mode: %s  %d", elvMotor.getMode(), elvMotor.getCurrentPosition());
        telemetry.addData("pos  ", String.format("x:%4.0f y:%4.0f h:%6.2f", positionX, positionY, heading));
        //telemetry.addData("joy", String.format("%.2f %.2f",  xValue, yValue));
        //telemetry.addData("pos", String.format("x:%4.0f y:%4.0f h:%3.0f", positionX, positionY, Math.toDegrees(heading)));
        //telemetry.addData("rot", String.format("p:%3.0f r:%3.0f h:%3.0f", orientation.getPitch(), orientation.getRoll(), orientation.getHeading()));
        //telemetry.addData("touch", armTouch != null ? armTouch.isPressed() : "null");
        //telemetry.addData("arm", String.format("%s %.2f %d %s", onArmReset, elvMotor.getPower(), elvMotor.getCurrentPosition(), elvMotor.getMode().toString()));
        //telemetry.addData("distance", String.format("%.2f", opticalDistanceSensor.getLightDetected()));
    }
}

