package iax.protocol.call.state;

import iax.protocol.call.Call;
import iax.protocol.call.command.recv.CallCommandRecvFacade;
import iax.protocol.frame.DTMFFrame;
import iax.protocol.frame.Frame;
import iax.protocol.frame.ProtocolControlFrame;

/**
 * Defines the abstract methods for changing the call state and handling the frames
 *
 */
public abstract class CallState {

    /**
     * Handles the frame received
     * @param call the call from wich the frame was received
     * @param frame the frame received
     */
    public void handleRecvFrame(Call call, Frame frame) {
        try {
            // By default handles the nexts protocol control frames: hangup, lag request and ping
            if (frame.getType() == Frame.PROTOCOLCONTROLFRAME_T) {
                // Received a protocol control frame
                ProtocolControlFrame protocolControlFrame = (ProtocolControlFrame) frame;
                switch (protocolControlFrame.getSubclass()) {  
                case ProtocolControlFrame.ACK_SC:
                    CallCommandRecvFacade.ack(call, protocolControlFrame);
                    break;
                case ProtocolControlFrame.HANGUP_SC:
                    CallCommandRecvFacade.hangup(call, protocolControlFrame);
                    call.setState(Initial.getInstance());
                    call.endCall();
                    break;
                case ProtocolControlFrame.INVAL_SC:
                    call.setState(Initial.getInstance());
                    call.endCall();
                    break;
                case ProtocolControlFrame.LAGRQ_SC:
                    CallCommandRecvFacade.lagrq(call, protocolControlFrame);
                    break;
                case ProtocolControlFrame.PING_SC:
                    CallCommandRecvFacade.ping(call, protocolControlFrame);
                    break;
                case ProtocolControlFrame.PONG_SC:
                    CallCommandRecvFacade.pong(call, protocolControlFrame);
                    break;
                case ProtocolControlFrame.UNSUPPORT_SC:
                    CallCommandRecvFacade.unsupport(call, protocolControlFrame);
                    break;
                default:           
                    break;
                }
            } else if (frame.getType() == Frame.DTMFFRAME_T) {
                DTMFFrame dtmfFrame = (DTMFFrame) frame;
                char dtmfKey = (char) dtmfFrame.getSubclass();
                handleDTFMFrame(dtmfKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the frame to send
     * @param call call for sending the frame
     * @param frame the frame to send
     */
    public void handleSendFrame(Call call, Frame frame) {
        try {
            // By default handles the nexts protocol control frames: ack, lag reply, hangup, and pong
            if (frame.getType() == Frame.PROTOCOLCONTROLFRAME_T) {
                // Sending a protocol control frame
                ProtocolControlFrame protocolControlFrame = (ProtocolControlFrame) frame;
                switch (protocolControlFrame.getSubclass()) {
                case ProtocolControlFrame.ACK_SC:
                    call.sendFrameAndNoWait(protocolControlFrame);
                    break;
                case ProtocolControlFrame.LAGRP_SC:
                    call.sendFullFrameAndWaitForAck(protocolControlFrame);
                    break;
                case ProtocolControlFrame.HANGUP_SC:
                    call.sendFullFrameAndWaitForAck(protocolControlFrame);
                    call.setState(Initial.getInstance());
                    call.endCall();
                    break;
                case ProtocolControlFrame.PING_SC:
                    call.sendFullFrameAndWaitForRep(protocolControlFrame);
                    break;
                case ProtocolControlFrame.QUELCH_SC:
                    call.sendFullFrameAndWaitForAck(protocolControlFrame);
                    call.holdCall();
                    break;
                case ProtocolControlFrame.PONG_SC:
                    call.sendFullFrameAndWaitForAck(protocolControlFrame);
                    break;
                case ProtocolControlFrame.UNQUELCH_SC:
                    call.sendFullFrameAndWaitForAck(protocolControlFrame);
                    call.unHoldCall();
                    break;
                case ProtocolControlFrame.UNSUPPORT_SC:
                    call.sendFullFrameAndWaitForAck(protocolControlFrame);
                    break;
                default:
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDTFMFrame(char dtmfKey) {
        switch (dtmfKey) {
            case '0':
                System.out.println(dtmfKey);
                break;
            case '1':
                System.out.println(dtmfKey);
                break;
            case '2':
                System.out.println(dtmfKey);
                break;
            case '3':
                System.out.println(dtmfKey);
                break;
            case '4':
                System.out.println(dtmfKey);
                break;
            case '5':
                System.out.println(dtmfKey);
                break;
            case '6':
                System.out.println(dtmfKey);
                break;
            case '7':
                System.out.println(dtmfKey);
                break;
            case '8':
                System.out.println(dtmfKey);
                break;
            case '9':
                System.out.println(dtmfKey);
                break;
            case 'A':
                System.out.println(dtmfKey);
                break;
            case 'B':
                System.out.println(dtmfKey);
                break;
            case 'C':
                System.out.println(dtmfKey);
                break;
            case 'D':
                System.out.println(dtmfKey);
                break;
            case '*':
                System.out.println(dtmfKey);
                break;
            case '#':
                System.out.println(dtmfKey);
                break;
            default:
                System.out.println("illegal dtmfKey! ");
                break;
        }
    }
}