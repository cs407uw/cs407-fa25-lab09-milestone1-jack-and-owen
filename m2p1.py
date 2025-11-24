import numpy as np
import matplotlib.pyplot as plt

def load_data(filename):
    # Returns the data from the specified CSV file
    return np.loadtxt(filename, delimiter=',', skiprows=1)

def create_acceleration_plot(data):
    plt.figure()
    plt.plot(data[:, 0], data[:, 1])
    plt.plot(data[:, 0], data[:, 2])
    plt.xlabel('Time (s)')
    plt.ylabel('Acceleration (m/s^2)')
    plt.title('Acceleration and Noisy Acceleration')
    plt.legend(['Acceleration', 'Noisy Acceleration'])
    plt.savefig('data/acceleration.png')
    plt.close()

def create_speed_plot(data):
    plt.figure()
    time = data[:, 0]
    accel_true = data[:, 1]
    accel_noisy = data[:, 2]
    dt = np.diff(time)

    v_true = np.concatenate(([0], np.cumsum(0.5 * (accel_true[1:] + accel_true[:-1]) * dt)))
    v_noisy = np.concatenate(([0], np.cumsum(0.5 * (accel_noisy[1:] + accel_noisy[:-1]) * dt)))

    plt.plot(data[:, 0], v_true)
    plt.plot(data[:, 0], v_noisy)
    plt.xlabel('Time (s)')
    plt.ylabel('Speed (m/s)')
    plt.title('Speed')
    plt.legend(['True Speed', 'Noisy Speed'])
    plt.savefig('data/speed.png')
    plt.close()

def create_distance_plot(data):
    plt.figure()
    time = data[:, 0]
    accel_true = data[:, 1]
    accel_noisy = data[:, 2]
    dt = np.diff(time)

    # First calculate speed (integrate acceleration)
    v_true = np.concatenate(([0], np.cumsum(0.5 * (accel_true[1:] + accel_true[:-1]) * dt)))
    v_noisy = np.concatenate(([0], np.cumsum(0.5 * (accel_noisy[1:] + accel_noisy[:-1]) * dt)))
    
    # Then calculate distance (integrate speed)
    distance_true = np.concatenate(([0], np.cumsum(0.5 * (v_true[1:] + v_true[:-1]) * dt)))
    distance_noisy = np.concatenate(([0], np.cumsum(0.5 * (v_noisy[1:] + v_noisy[:-1]) * dt)))

    plt.plot(data[:, 0], distance_true)
    plt.plot(data[:, 0], distance_noisy)
    plt.xlabel('Time (s)')
    plt.ylabel('Distance (m)')
    plt.title('Distance')
    plt.legend(['True Distance', 'Noisy Distance'])

    print(f"True Distance: {distance_true[-1]}")
    print(f"Noisy Distance: {distance_noisy[-1]}")
    print(f"Difference: {distance_true[-1] - distance_noisy[-1]}")

    plt.savefig('data/distance.png')
    plt.close()

if __name__ == "__main__":
    data = load_data('data/ACCELERATION.csv')
    create_acceleration_plot(data)
    create_speed_plot(data)
    create_distance_plot(data)