package mine.archive.adapter_pattern.ex2;

// 目标接口：标准6分水管
interface SixInchPipe {
    void connectWater(); // 6分口连接规范
}

// 适配者：老式4分水龙头（已存在但接口不兼容）
class FourInchFaucet {
    public void outputWater() {
        System.out.println("🚰 4分口径出水");
    }
}

// 适配器：4分转6分转接头（物理世界的对象适配器！）
class PipeAdapter implements SixInchPipe {
    private FourInchFaucet faucet;  // 组合老式水龙头

    public PipeAdapter(FourInchFaucet faucet) {
        this.faucet = faucet;
    }

    @Override
    public void connectWater() {
        // 核心转换逻辑：物理转接+规格适配
        System.out.println("🔧 安装4分转6分接头");
        faucet.outputWater();  // 调用原有功能
        System.out.println("💧 转换为6分管道输水");
    }
}

// 客户端：新房子的6分水管系统
public class NewHousePlumbing {
    public static void main(String[] args) {
        FourInchFaucet oldFaucet = new FourInchFaucet();
        SixInchPipe sixInchPipe = new PipeAdapter(oldFaucet);
        
        sixInchPipe.connectWater();
        /* 输出：
           🔧 安装4分转6分接头
           🚰 4分口径出水
           💧 转换为6分管道输水
        */
    }
}